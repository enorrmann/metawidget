'use strict';

/* Services */

angular.module('addressBookServices', []).
	factory('contacts', function() {
		var _all = [
		            	{
		            	 "id": 1,
		        		 "firstname": "Homer",
		        		 "surname": "Simpson",
		        		 "communications": "0402 123 456",
		        		 "type": "personal"
		            	},
		        		{
		        		 "id": 2,
		        		 "firstname": "Marge",
		        		 "surname": "Simpson",
		        		 "communications": "0402 123 789",
		        		 "type": "personal"
		        		},
		        		{
		        		 "id": 3,
		        		 "firstname": "Montgomery",
		        		 "surname": "Burns",
		        		 "communications": "0402 123 999",
		        		 "type": "business"
		        		}
					];
	
		return _all;
	});