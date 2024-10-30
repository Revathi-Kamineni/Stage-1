// package com.revature.service;
// import java.util.HashMap;
// import java.util.Map;
// import com.revature.model.Chef;


// /**
//  * The AuthenticationService class provides authentication functionality
//  * for Chef objects. It manages the login, logout, and registration
//  * processes, as well as session management for chefs. This service 
//  * utilizes a ChefService to perform operations related to chefs and 
//  * maintains a session map to track active sessions.
//  */

// public class AuthenticationService {

//     /**
//      * The service used for managing Chef objects and their operations.
//      */

//     @SuppressWarnings("unused")
//     private ChefService chefService;

//     /**
//      * A map that stores active sessions for chefs, indexed by session token.
//      */

//     @SuppressWarnings("unused")
//     private Map<String, Chef> sessionMap;

//     /**
//      * Constructs an AuthenticationService with the specified ChefService.
//      *
//      * @param chefService the ChefService to be used by this authentication service
//      */

//     public AuthenticationService(ChefService chefService) {
//         this.chefService = chefService;
//         this.sessionMap = new HashMap<>(); // Initializing the session map
//     }

//     /**
//      * Logs in a chef by validating their credentials and creating a session.
//      *
//      * @param chef the Chef object containing login credentials
//      * @return a session token if the login is successful; null otherwise
//      */

//     public String login(Chef chef) {
       
//         // Implementation

//         return null; //return a session token if the login is successful; null otherwise

//     }

//     /**
//      * Logs out a chef by invalidating their session token.
//      *
//      * @param token the session token of the chef to be logged out
//      */

//     public void logout(String token) {
//         // Implementation
//     }

//     /**
//      * Registers a new chef and adds them to the system.
//      *
//      * @param chef the Chef object to be registered
//      * @return the registered Chef object with any generated fields populated
//      */

//     public Chef registerChef(Chef chef) {
//         // Implementation
//         return chef; //return he registered Chef object with any generated fields populated

//     }

//     /**
//      * Retrieves a Chef object from the session token.
//      *
//      * @param token the session token used to retrieve the chef
//      * @return the Chef object associated with the session token; null if not found
//      */
	
//     public Chef getChefFromSessionToken(String token) {
//         // Implementation
//         return null; //return the Chef object associated with the session token; null if not found

//     }
// }

package com.revature.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.revature.model.Chef;
//import com.revature.dtos.LoginRequest;

public class AuthenticationService {

	private ChefService chefService;

	private Map<String, Chef> sessionMap = new HashMap<String, Chef>();

	public AuthenticationService(ChefService chefService) {
		super();
		this.chefService = chefService;
	}

	public String login(Chef chef) {
		List<Chef> existingChefs = chefService.searchChefs(chef.getUsername());
			for (Chef c: existingChefs) {
				if (c.getUsername().equals(chef.getUsername()) && c.getPassword().equals(chef.getPassword())) {
					String token = UUID.randomUUID().toString();
					sessionMap.put(token, c);
					return token;
				}
			}
			
			return null;
		
	}
	
	public void logout(String token) {
		sessionMap.remove(token);
	}
	
	public Chef registerChef(Chef chef) {
		chefService.saveChef(chef);
		return chef;
	}
	
	public Chef getChefFromSessionToken(String token) {
		return sessionMap.get(token);
	}

}