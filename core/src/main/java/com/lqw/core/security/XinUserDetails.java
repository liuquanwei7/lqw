package com.lqw.core.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */

public class XinUserDetails extends LqwUserDetails {
	private static final long serialVersionUID = 2109189737183631436L;

	private final long id;

	private String password;

	private final String username;

	private final Set<GrantedAuthority> authorities;

	private final boolean accountNonExpired;

	private final boolean accountNonLocked;

	private final boolean credentialsNonExpired;

	private final boolean enabled;

	private long shiyanshiId;

	private long zhutidanweiId;

	private long xitongId;

	private long yuangongId;

	private String unionId;

	private String xcxopenId;

	public XinUserDetails(long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(id, username, password, true, true, true, true, authorities);
	}

	public XinUserDetails(long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(false, "web");
		if (username == null || "".equals(username) || password == null) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public void eraseCredentials() {
		this.password = null;
	}

	public long getId() {
		return this.id;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}
		return sortedAuthorities;
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = 510L;

		private AuthorityComparator() {}

		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			if (g2.getAuthority() == null) {
				return -1;
			}
			if (g1.getAuthority() == null) {
				return 1;
			}
			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof XinUserDetails) {
			return this.username.equals(((XinUserDetails)rhs).username);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired)
						.append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
		if (!this.authorities.isEmpty()) {
			sb.append("Granted Authorities: ");
			boolean first = true;
			for (GrantedAuthority auth : this.authorities) {
				if (!first)
					sb.append(",");
				first = false;
				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}
		return sb.toString();
	}

	public long getShiyanshiId() {
		return this.shiyanshiId;
	}

	public void setShiyanshiId(long shiyanshiId) {
		this.shiyanshiId = shiyanshiId;
	}

	public long getZhutidanweiId() {
		return this.zhutidanweiId;
	}

	public void setZhutidanweiId(long zhutidanweiId) {
		this.zhutidanweiId = zhutidanweiId;
	}

	public long getXitongId() {
		return this.xitongId;
	}

	public void setXitongId(long xitongId) {
		this.xitongId = xitongId;
	}

	public long getYuangongId() {
		return this.yuangongId;
	}

	public void setYuangongId(long yuangongId) {
		this.yuangongId = yuangongId;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getXcxopenId() {
		return this.xcxopenId;
	}

	public void setXcxopenId(String xcxopenId) {
		this.xcxopenId = xcxopenId;
	}
}
