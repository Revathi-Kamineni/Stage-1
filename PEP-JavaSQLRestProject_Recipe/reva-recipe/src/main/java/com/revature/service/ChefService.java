// // package com.revature.service;
// // import java.util.List;
// // import java.util.Optional;
// // import com.revature.model.Chef;
// // import com.revature.dao.ChefDAO;
// // import com.revature.util.Page;

// // /**
// //  * The ChefService class provides services related to Chef objects,
// //  * including CRUD operations and search functionalities. It acts as a 
// //  * bridge between the data access layer (ChefDao) and the application 
// //  * logic, ensuring that all operations on Chef objects are managed 
// //  * consistently and efficiently.
// //  */

// // public class ChefService {

// //     /**
// //      * The data access object used for performing operations on Chef entities.
// //      */

// //     @SuppressWarnings("unused")
// //     private ChefDAO chefDao;

// //     /**
// //      * Constructs a ChefService with the specified ChefDao.
// //      *
// //      * @param chefDao the ChefDao to be used by this service for data access
// //      */

// //     public ChefService(ChefDAO chefDao) {
// //         this.chefDao = chefDao;
// //     }

// //     /**
// //      * Finds a Chef by their unique identifier.
// //      *
// //      * @param id the unique identifier of the chef to be found
// //      * @return an Optional containing the found Chef if present; 
// //      *         an empty Optional if not found
// //      */

// //     public Optional<Chef> findChef(int id) {
// //         // Implementation
// //         return null; //return an Optional containing the found Chef if present; an empty Optional if not found

// //     }

// //     /**
// //      * Saves a Chef object to the data store.
// //      *
// //      * @param chef the Chef object to be saved
// //      */

// //     public void saveChef(Chef chef) {
// //         // Implementation
// //     }

// //     /**
// //      * Searches for chefs based on a search term.
// //      *
// //      * @param term the search term used to find chefs
// //      * @return a list of Chef objects that match the search term
// //      */

// //     public List<Chef> searchChefs(String term) {
// //         // Implementation
// //         return null; //return a list of Chef objects that match the search term

      
// //     }

// //     /**
// //      * Deletes a Chef by their unique identifier.
// //      *
// //      * @param id the unique identifier of the chef to be deleted
// //      */

// //     public void deleteChef(int id) {
// //         // Implementation
// //     }

// //     /**
// //      * Searches for chefs with pagination and sorting options.
// //      *
// //      * @param term the search term used to find chefs
// //      * @param page the page number to retrieve
// //      * @param pageSize the number of chefs per page
// //      * @param sortBy the field by which to sort the results
// //      * @param sortDirection the direction of sorting (ascending or descending)
// //      * @return a Page containing the results of the search
// //      */
	
// //     public Page<Chef> searchChefs(String term, int page, int pageSize, String sortBy, String sortDirection) {
// //         // Implementation
// //         return null; //return a Page containing the results of the search

// //     }
// // }


// package com.revature.service;

// import java.util.List;
// import java.util.Optional;
// import com.revature.model.Chef;
// import com.revature.dao.ChefDAO;
// import com.revature.util.Page;

// /**
//  * The ChefService class provides services related to Chef objects,
//  * including CRUD operations and search functionalities. It acts as a 
//  * bridge between the data access layer (ChefDao) and the application 
//  * logic, ensuring that all operations on Chef objects are managed 
//  * consistently and efficiently.
//  */
// public class ChefService {

//     /**
//      * The data access object used for performing operations on Chef entities.
//      */
//     private ChefDAO chefDao;

//     /**
//      * Constructs a ChefService with the specified ChefDao.
//      *
//      * @param chefDao the ChefDao to be used by this service for data access
//      */
//     public ChefService(ChefDAO chefDao) {
//         this.chefDao = chefDao;
//     }

//     /**
//      * Finds a Chef by their unique identifier.
//      *
//      * @param id the unique identifier of the chef to be found
//      * @return an Optional containing the found Chef if present; 
//      *         an empty Optional if not found
//      */
//     public Optional<Chef> findChef(int id) {
//         return Optional.of(chefDao.getChefById(id)); // Assuming findById is implemented in ChefDAO
//     }

//     /**
//      * Saves a Chef object to the data store.
//      *
//      * @param chef the Chef object to be saved
//      */
//     public void saveChef(Chef chef) {
//         chefDao.createChef(chef); // Assuming save is implemented in ChefDAO
//     }

//     /**
//      * Searches for chefs based on a search term.
//      *
//      * @param term the search term used to find chefs
//      * @return a list of Chef objects that match the search term
//      */
//     public List<Chef> searchChefs(String term) {
//         return chefDao.searchChefsByTerm(term); // Assuming searchByTerm is implemented in ChefDAO
//     }

//     /**
//      * Deletes a Chef by their unique identifier.
//      *
//      * @param id the unique identifier of the chef to be deleted
//      */
//     public void deleteChef(int id) {
//         chefDao.deleteChef(id); // Assuming delete is implemented in ChefDAO
//     }

//     /**
//      * Searches for chefs with pagination and sorting options.
//      *
//      * @param term the search term used to find chefs
//      * @param page the page number to retrieve
//      * @param pageSize the number of chefs per page
//      * @param sortBy the field by which to sort the results
//      * @param sortDirection the direction of sorting (ascending or descending)
//      * @return a Page containing the results of the search
//      */
//     // public Page<Chef> searchChefs(String term, int page, int pageSize, String sortBy, String sortDirection) {
//     //     return chefDao.searchWithPagination(term, page, pageSize, sortBy, sortDirection); // Assuming searchWithPagination is implemented in ChefDAO
//     // }
// }


package com.revature.service;

import java.util.List;
import java.util.Optional;

import com.revature.model.Chef;
import com.revature.model.Recipe;
import com.revature.dao.ChefDAO;
import com.revature.util.Page;
import com.revature.util.PageOptions;

public class ChefService {
	private ChefDAO chefDao;

	public ChefService(ChefDAO chefDao) {
	        this.chefDao = chefDao;
	    }

        public Optional<Chef> findChef(int id) {
            return Optional.ofNullable(chefDao.getChefById(id));
        }
    // public Optional<Chef> findChefByUsername(String username) {
	// 	return Optional.ofNullable(chefDao.getChefByUsername(username));
	// }

	public void saveChef(Chef chef) {
		if (chef.getId() == 0) {
			int id = chefDao.createChef(chef);
			chef.setId(id);
		} else {
			chefDao.updateChef(chef);
		}
	}

	public List<Chef> searchChefs(String term) {
		if (term == null) { // || term.isBlank()
			return chefDao.getAllChefs();
		} else {
			return chefDao.searchChefsByTerm(term);
		}
	}

	public void deleteChef(int id) {
		Chef chef = chefDao.getChefById(id);
		if (chef != null) {
			chefDao.deleteChef(chef);
		}
      //  chefDao.getChefById(id);
	}

	public Page<Chef> searchChefs(String term, int page, int pageSize, String sortBy, String sortDirection) {
		PageOptions options = new PageOptions(page, pageSize, sortBy, sortDirection);
		if (term == null ) { //|| term.isBlank()
			return chefDao.getAllChefs(options);
		} else {
			return chefDao.searchChefsByTerm(term, options);
		}
	}
}