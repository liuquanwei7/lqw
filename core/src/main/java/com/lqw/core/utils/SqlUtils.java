package com.lqw.core.utils;

import java.util.*;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class SqlUtils {
	public static class Select {
		private Class<?> table;

		private String alias;

		private List<Class<?>> columnTable = new ArrayList<>();

		private int tableCount = 0;

		private Map<Class<?>, String> table2alias = new HashMap<>();

		private List<SqlUtils.Join> join_list = new ArrayList<>();

		private List<SqlUtils.Where> where_list = new ArrayList<>();

		private List<SqlUtils.Group> group_list = new ArrayList<>();

		private List<SqlUtils.Order> order_list = new ArrayList<>();

		private Map<String, Object> parameters = new HashMap<>();

		public static Select valueOf(Class<?> table) {
			Select result = new Select(table);
			return result;
		}

		private Select(Class<?> table) {
			this.table = table;
			this.alias = "t" + this.tableCount++;
			this.table2alias.put(this.table, this.alias);
		}

		public Select addColumns(Class<?> table) {
			this.columnTable.add(table);
			return this;
		}

		public Select join(SqlUtils.Join table) {
			this.join_list.add(table);
			table.alias = "t" + this.tableCount++;
			this.table2alias.put(table.clazz, table.alias);
			return this;
		}

		public SqlUtils.Join left(Class<?> table) {
			return join(table, SqlUtils.JoinType.left);
		}

		public SqlUtils.Join right(Class<?> table) {
			return join(table, SqlUtils.JoinType.right);
		}

		public SqlUtils.Join inner(Class<?> table) {
			return join(table, SqlUtils.JoinType.inner);
		}

		public SqlUtils.Join join(Class<?> table, SqlUtils.JoinType mode) {
			SqlUtils.Join result = SqlUtils.Join.create(table, mode);
			result.select = this;
			join(result);
			return result;
		}

		public Select where(SqlUtils.Where where) {
			this.where_list.add(where);
			return this;
		}

		public Select order(SqlUtils.Order order) {
			this.order_list.add(order);
			return this;
		}

		public Select group(SqlUtils.Group group) {
			this.group_list.add(group);
			return this;
		}

		public Map<String, Object> getParameters() {
			return this.parameters;
		}

		private int handleWhere(String relation, boolean p_hasWhere, boolean p_first, int p_propertyNum, StringBuilder sb, List<SqlUtils.Where> where_list) {
			boolean hasWhere = p_hasWhere;
			boolean first = p_first;
			int propertyNum = p_propertyNum;
			for (SqlUtils.Where where : where_list) {
				if (where.sub != null) {
					if (!hasWhere) {
						sb.append(" where ");
						hasWhere = true;
					}
					if (first) {
						first = false;
					} else {
						sb.append(" " + relation + " ");
					}
					sb.append(" ( ");
					propertyNum += handleWhere(where.relation, hasWhere, true, propertyNum, sb, where.sub);
					sb.append(" ) ");
				}
				if (where.value == null)
					continue;
				if (!hasWhere) {
					sb.append(" where ");
					hasWhere = true;
				}
				String alias = null;
				if (where.clazz != null) {
					alias = this.table2alias.get(where.clazz);
				} else {
					alias = where.alias;
				}
				String bindName = alias + "_" + where.property + "_" + propertyNum++;
				if (first) {
					first = false;
				} else {
					sb.append(" " + relation + " ");
				}
				if (where.op.equals("is null") || where.op.equals("is not null")) {
					sb.append(alias + "." + where.property + " " + where.op);
					continue;
				}
				sb.append(alias + "." + where.property + " " + where.op + " :" + bindName);
				if (where.value instanceof Enum) {
					this.parameters.put(bindName, ((Enum)where.value).name());
					continue;
				}
				this.parameters.put(bindName, where.value);
			}
			return propertyNum;
		}

		public String buildSql() {
			String columns = "";
			if (this.columnTable.isEmpty()) {
				columns = columns + "*";
			} else {
				boolean first = true;
				for (Class<?> table : this.columnTable) {
					if (!first) {
						columns = columns + ",";
						first = false;
					}
					String alias = this.table2alias.get(table);
					columns = columns + alias + ".*";
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append("select " + columns + " from " + this.table.getSimpleName() + " " + this.alias + " ");
			for (SqlUtils.Join join : this.join_list) {
				sb.append(" " + join.mode + " " + join.clazz.getSimpleName() + " " + join.alias + " ");
				List<SqlUtils.On> on_List = join.on_list;
				boolean hasOn = false;
				for (SqlUtils.On on : on_List) {
					if (!hasOn) {
						sb.append(" ON ");
						hasOn = true;
					}
					String newAlias = this.table2alias.get(on.newt);
					sb.append(" " + join.alias + "." + on.property + " = " + newAlias + "." + on.newt_property);
				}
			}
			int propertyNum = handleWhere("and", false, true, 0, sb, this.where_list);
			boolean hasGroup = false;
			boolean firstGroup = true;
			for (SqlUtils.Group group : this.group_list) {
				if (!hasGroup) {
					sb.append(" GROUP BY ");
					hasGroup = true;
				}
				String alias = this.table2alias.get(group.clazz);
				if (firstGroup) {
					sb.append(alias + "." + group.property + " ");
					firstGroup = false;
					continue;
				}
				sb.append("," + alias + "." + group.property + " ");
			}
			boolean hasOrder = false;
			boolean firstOrder = true;
			for (SqlUtils.Order order : this.order_list) {
				if (!hasOrder) {
					sb.append(" ORDER BY ");
					hasOrder = true;
				}
				String alias = null;
				if (order.where != null) {
					alias = this.table2alias.get(order.where.clazz);
				} else {
					alias = this.table2alias.get(order.clazz);
				}
				if (firstOrder) {
					firstOrder = false;
				} else {
					sb.append(",");
				}
				if (order.where != null) {
					String wh = "";
					if (order.where.op.equals("is null") || order.where.op.equals("is not null")) {
						wh = alias + "." + order.where.property + " " + order.where.op;
					} else {
						String bindName = alias + "_" + order.where.property + "_" + propertyNum++;
						wh = alias + "." + order.where.property + " " + order.where.op + " :" + bindName;
						this.parameters.put(bindName, order.where.value);
					}
					sb.append(wh + " " + order.type + " ");
					continue;
				}
				sb.append(alias + "." + order.property + " " + order.type + " ");
			}
			return sb.toString();
		}
	}

	public static class Join {
		SqlUtils.Select select;

		Class<?> clazz;

		String alias;

		String mode;

		List<SqlUtils.On> on_list = new ArrayList<>();

		public static Join create(Class<?> table, SqlUtils.JoinType mode) {
			Join r = new Join();
			r.clazz = table;
			r.mode = mode.sql;
			return r;
		}

		public static Join left(Class<?> table) {
			Join r = new Join();
			r.clazz = table;
			r.mode = SqlUtils.JoinType.left.sql;
			return r;
		}

		public static Join right(Class<?> table) {
			Join r = new Join();
			r.clazz = table;
			r.mode = SqlUtils.JoinType.right.sql;
			return r;
		}

		public static Join inner(Class<?> table) {
			Join r = new Join();
			r.clazz = table;
			r.mode = SqlUtils.JoinType.inner.sql;
			return r;
		}

		public Join on(String property, Class<?> newT, String newT_property) {
			SqlUtils.On on = SqlUtils.On.create(property, newT, newT_property);
			this.on_list.add(on);
			return this;
		}

		public String getAlias() {
			return this.alias;
		}

		public SqlUtils.Select and() {
			return this.select;
		}
	}

	public static class On {
		String property;

		Class<?> newt;

		String newt_property;

		public static On create(String property, Class<?> newt, String newt_property) {
			On r = new On();
			r.property = property;
			r.newt = newt;
			r.newt_property = newt_property;
			return r;
		}
	}

	public static class Where {
		Class<?> clazz;

		String alias;

		String property;

		Object value;

		String op;

		String relation;

		List<Where> sub;

		public String toString() {
			return "Where [clazz=" + this.clazz + ", alias=" + this.alias + ", property=" + this.property + ", op=" + this.op + "]";
		}

		public static Where or(Where... sub) {
			Where r = new Where();
			r.sub = new ArrayList<>();
			r.relation = "or";
			Collections.addAll(r.sub, sub);
			return r;
		}

		public static Where and(Where... sub) {
			Where r = new Where();
			r.sub = new ArrayList<>();
			r.relation = "and";
			Collections.addAll(r.sub, sub);
			return r;
		}

		public static Where eq(Class<?> clazz, String property, Object value) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.value = value;
			r.op = "=";
			return r;
		}

		public static Where eq(String alias, String property, Object value) {
			Where r = new Where();
			r.alias = alias;
			r.property = property;
			r.value = value;
			r.op = "=";
			return r;
		}

		public static Where ne(Class<?> clazz, String property, Object value) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.value = value;
			r.op = "<>";
			return r;
		}

		public static Where ne(String alias, String property, Object value) {
			Where r = new Where();
			r.alias = alias;
			r.property = property;
			r.value = value;
			r.op = "<>";
			return r;
		}

		public static Where like(Class<?> clazz, String property, Object value) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.value = value;
			r.op = "like";
			return r;
		}

		public static Where like(String alias, String property, Object value) {
			Where r = new Where();
			r.alias = alias;
			r.property = property;
			r.value = value;
			r.op = "like";
			return r;
		}

		public static Where gt(Class<?> clazz, String property, Object value) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.value = value;
			r.op = ">";
			return r;
		}

		public static Where ge(Class<?> clazz, String property, Object value) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.value = value;
			r.op = ">=";
			return r;
		}

		public static Where lt(Class<?> clazz, String property, Object value) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.value = value;
			r.op = "<";
			return r;
		}

		public static Where le(Class<?> clazz, String property, Object value) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.value = value;
			r.op = "<=";
			return r;
		}

		public static Where isNull(Class<?> clazz, String property) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.op = "is null";
			r.value = "";
			return r;
		}

		public static Where isNotNull(Class<?> clazz, String property) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.op = "is not null";
			r.value = "";
			return r;
		}

		public static Where in(Class<?> clazz, String property, List<Long> value) {
			Where r = new Where();
			r.clazz = clazz;
			r.property = property;
			r.value = value;
			r.op = "in";
			return r;
		}
	}

	public static class Order {
		String type;

		Class<?> clazz;

		String property;

		SqlUtils.Where where;

		public static Order asc(Class<?> clazz, String property) {
			Order r = new Order();
			r.clazz = clazz;
			r.property = property;
			r.type = "asc";
			return r;
		}

		public static Order desc(Class<?> clazz, String property) {
			Order r = new Order();
			r.clazz = clazz;
			r.property = property;
			r.type = "desc";
			return r;
		}

		public static Order asc(SqlUtils.Where where) {
			Order r = new Order();
			r.where = where;
			r.type = "asc";
			return r;
		}

		public static Order desc(SqlUtils.Where where) {
			Order r = new Order();
			r.where = where;
			r.type = "desc";
			return r;
		}
	}

	public static class Group {
		Class<?> clazz;

		String property;

		public static Group group(Class<?> clazz, String property) {
			Group r = new Group();
			r.clazz = clazz;
			r.property = property;
			return r;
		}
	}

	public enum JoinType {
		inner("inner join"),
		left("left join"),
		right("right join");

		private String sql;

		JoinType(String sql) {
			this.sql = sql;
		}

		public String getSql() {
			return this.sql;
		}
	}

	public enum OrderType {
		asc, desc;
	}

	public static void main(String[] args) {}
}
