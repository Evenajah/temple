package com.cdgs.temple.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cdgs.temple.entity.MemberEntity;
import com.cdgs.temple.repository.MemberRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	private MemberRepository memberRepository;

	@Autowired
	public JwtUserDetailsServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberEntity member = memberRepository.findByMemberUsername(username);
		if(member == null) {
			throw new UsernameNotFoundException(String.format("Not found username '%s'", username));
			
		}else {
			return JwtUserFactory.create(member);
		}
	}
	
	
}
