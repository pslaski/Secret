package pl.secret.admin

import pl.secret.*
import pl.secret.security.*
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class AdminEditController {
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static defaultAction = "list"
	def springSecurityService
	def adminService

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		return adminService.getAdmins()
	}
	
	def show = {
		def user = Admin.get(params.id)
		render view: "show", model: [admin: user]
	}
	
	def edit = {
		def user = Admin.get(params.id)
		println "!!!!!!!!!!!"+user
		render view: "edit", model: [admin: user]
	}	
	
	def update = {
		
		def admin = Admin.get(params.id)
		println "!!!!!!!!!!"+admin.username
		bindData(admin, params, ['id'])
		try {
			
			if (!admin.validate()) {
				throw e
			}
			adminService.updateAdmin(admin)
			} catch(e) {
			render view: "edit", model: [admin: admin]
		}
		flash.message = "admin.admin.updated"
		redirect action: "list"
	}

    def editPassword = {
		[passwordCommand: new UserPassswordChangeCommand(id: params.int("id"))]
	}
	
	
	def delete = {
        def user = Admin.get(params.id)
		
        flash.message = "admin.admin.deleted"
        redirect action: 'list'
    }
	
	def changePassword = { UserPassswordChangeCommand passwordCommand ->
		if (passwordCommand.hasErrors()) {
			render view: "editPassword", model: [passwordCommand: passwordCommand]
			return
		}
		def user = Admin.get(passwordCommand.id)
		user.password = springSecurityService.encodePassword(passwordCommand.password)
		try {
			adminService.updateAdmin(user)
		} catch (e) {
			render view: 'editPassword', model: [passwordCommand: passwordCommand]
			return
		}
		redirect action: 'edit', id: passwordCommand.id
	}

    def create = {
        [admin: new NewAdminCommand()]
    }

    def save = { NewAdminCommand command ->
        def admin = new Admin(command.properties)
		admin.password = springSecurityService.encodePassword(admin.password)
		admin.validate()
		if(command.hasErrors() || admin.hasErrors()) {
			admin.errors.allErrors.each {
				command.errors.rejectValue(it.field, it.code, it.arguments, it.defaultMessage)
			}
			render view: 'create', model: [admin: command]
			return
		}

		try {
			adminService.createAdmin(admin)
		} catch (e) {
			log.error(e)
			render view: 'create', model: [admin: command]
			return
		}
		flash.message = 'admin.creationSuccess'
		redirect action: 'list'

    }
} 

class NewAdminCommand {

	static constraints = {
		password blank: false, minSize: 6
		repeatPassword blank: false, minSize: 6,
		validator: {val, obj ->
			if (val != obj.password) {
				return "passwordsDoNotMatch"
			}
			return true
		}
	}
	String username
	String name
	String surname
	String password
	String repeatPassword
	boolean enabled = true
	boolean accountExpired = false
	boolean accountLocked = false 
	boolean passwordExpired = false
}
