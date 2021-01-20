
package com.grokonez.jwtauthentication.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@NoArgsConstructor
@Getter
@Setter
public class DeviceCollectedAuthData implements Serializable {

	/**
	 * generated serial version id
	 */
	private static final long serialVersionUID = -969857695481409943L;

	private String pan;


	private String name;

	private String dob;

}
