package com.nubes.ipl2020.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaxAmountPlayerByRoleDTO {
	
	private String role;	
	private String amount;
	private List<PlayerDTO> players;
	
	
		
}
