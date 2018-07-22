package herorepo

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class HeroController {

    HeroService heroService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond heroService.list(params), model:[heroCount: heroService.count()]
    }

    def show(Long id) {
        respond heroService.get(id)
    }

    def create() {
        respond new Hero(params)
    }

    def save(Hero hero) {
        if (hero == null) {
            notFound()
            return
        }

        try {
            heroService.save(hero)
        } catch (ValidationException e) {
            respond hero.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'hero.label', default: 'Hero'), hero.id])
                redirect hero
            }
            '*' { respond hero, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond heroService.get(id)
    }

    def update(Hero hero) {
        if (hero == null) {
            notFound()
            return
        }

        try {
            heroService.save(hero)
        } catch (ValidationException e) {
            respond hero.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'hero.label', default: 'Hero'), hero.id])
                redirect hero
            }
            '*'{ respond hero, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        heroService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'hero.label', default: 'Hero'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'hero.label', default: 'Hero'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
