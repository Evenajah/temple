package com.cdgs.temple.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cdgs.temple.entity.MemberEntity;

public class JwtUserFactory {

	public static JwtUser create(MemberEntity member) {

		return new JwtUser(member.getMemberId(), member.getMemberUsername(),
				member.getMemberPassword(), member, mapToGrantedAuthorities(
						new ArrayList<>(Arrays.asList("ROLE_" + member.getRole().getRoleName()))), true);
	}

	private static  List<GrantedAuthority> mapToGrantedAuthorities(ArrayList<String> authorities) {
		
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
	}

	
}
