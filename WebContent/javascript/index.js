function assignRight(role, right) {

	new Ajax.Request('roles/rolesRightsAssign.jsp', {
		method : 'post',
		parameters : {
			role : role,
			right : right
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function assignRole(user, role) {

	new Ajax.Request('users/usersRolesAssign.jsp', {
		method : 'post',
		parameters : {
			user : user,
			role : role
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function assignRightToRole(roleID) {

	new Ajax.Request('roles/rolesRightsAssign.jsp', {
		method : 'post',
		parameters : {
			roleID : roleID
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function assignRoleToUser(userID) {

	new Ajax.Request('users/usersRolesAssign.jsp', {
		method : 'post',
		parameters : {
			userID : userID
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function createDrink(drink, code, price, vol, count) {

	new Ajax.Request('drinks/drinksReg.jsp', {
		method : 'post',
		parameters : {
			drink : drink,
			code : code,
			price : price,
			vol : vol,
			count : count
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function createRole(role) {

	new Ajax.Request('roles/rolesReg.jsp', {
		method : 'post',
		parameters : {
			role : role
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function createUser(user) {

	new Ajax.Request('users/usersReg.jsp', {
		method : 'post',
		parameters : {
			user : user
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function deleteRole(role) {

	new Ajax.Request('roles/rolesDelete.jsp', {
		method : 'post',
		parameters : {
			role : role
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function deleteUser(user) {

	new Ajax.Request('users/usersDelete.jsp', {
		method : 'post',
		parameters : {
			user : user
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function editRole(role) {

	new Ajax.Request('roles/rolesEdit.jsp', {
		method : 'post',
		parameters : {
			role : role
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function editUser(user) {

	new Ajax.Request('users/usersEdit.jsp', {
		method : 'post',
		parameters : {
			user : user
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function login(user, password) {

	new Ajax.Request('index.jsp', {
		method : 'post',
		parameters : {
			benutzerName : user,
			passwort : password
		},
		onSuccess : function(response) {
			$('body').update(response.responseText);
		}
	});

}

function logout() {

	new Ajax.Request('users/usersLogout.jsp', {
		method : 'post',
		onSuccess : function(response) {
			window.location.replace("index.jsp");
		}
	});

}

function removeRight(role, right) {

	new Ajax.Request('roles/rolesRightsRemove.jsp', {
		method : 'post',
		parameters : {
			role : role,
			right : right
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function removeRole(user, role) {

	new Ajax.Request('users/usersRolesRemove.jsp', {
		method : 'post',
		parameters : {
			user : user,
			role : role
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function removeRightFromRole(roleID) {

	new Ajax.Request('roles/rolesRightsRemove.jsp', {
		method : 'post',
		parameters : {
			roleID : roleID
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function removeRoleFromUser(userID) {

	new Ajax.Request('users/usersRolesRemove.jsp', {
		method : 'post',
		parameters : {
			userID : userID
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function searchRole(role) {

	new Ajax.Request('roles/rolesSearch.jsp', {
		method : 'post',
		parameters : {
			role : role
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function searchUser(user) {

	new Ajax.Request('users/usersSearch.jsp', {
		method : 'post',
		parameters : {
			user : user
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function showAccountsSubMenu() {

	new Ajax.Request('account/kontoverwaltung.jsp', {
		method : 'post',
		onSuccess : function(response) {
			$('submenu').setStyle({
				borderBottom : '1px solid #000000'
			});
			$('submenu').update(response.responseText);
			$('container').update('');
			return false;
		}
	});

}

function showDrinksSubMenu() {

	new Ajax.Request('drinks/drinks.jsp', {
		method : 'post',
		onSuccess : function(response) {
			$('submenu').setStyle({
				borderBottom : '1px solid #000000'
			});
			$('submenu').update(response.responseText);
			$('container').update('');
			return false;
		}
	});

}

function showPropsSubMenu() {

	new Ajax.Request('sys/systemverwaltung.jsp', {
		method : 'post',
		onSuccess : function(response) {
			$('submenu').setStyle({
				borderBottom : '1px solid #000000'
			});
			$('submenu').update(response.responseText);
			$('container').update('');
			return false;
		}
	});

}

function showRolesSubMenu() {

	new Ajax.Request('roles/roles.jsp', {
		method : 'post',
		onSuccess : function(response) {
			$('submenu').setStyle({
				borderBottom : '1px solid #000000'
			});
			$('submenu').update(response.responseText);
			$('container').update('');
			return false;
		}
	});

}

function showUsersSubMenu() {

	new Ajax.Request('users/users.jsp', {
		method : 'post',
		onSuccess : function(response) {
			$('submenu').setStyle({
				borderBottom : '1px solid #000000'
			});
			$('submenu').update(response.responseText);
			$('container').update('');
			return false;
		}
	});

}

function updateRole(role, desc) {

	new Ajax.Request('roles/rolesUpdate.jsp', {
		method : 'post',
		parameters : {
			role : role,
			desc : desc
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}

function updateUser(user, rfid, benutzerbarcode, gesperrt) {

	new Ajax.Request('users/usersUpdate.jsp', {
		method : 'post',
		parameters : {
			user : user,
			rfid : rfid,
			benutzerbarcode : benutzerbarcode,
			gesperrt : gesperrt
		},
		onSuccess : function(response) {
			$('container').update(response.responseText);
		}
	});

}