package herorepo

import grails.gorm.services.Service

@Service(Hero)
interface HeroService {

    Hero get(Serializable id)

    List<Hero> list(Map args)

    Long count()

    void delete(Serializable id)

    Hero save(Hero hero)

}