package herorepo

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class HeroServiceSpec extends Specification {

    HeroService heroService
    SessionFactory sessionFactory

    private Long setupData() {
        Hero hero = new Hero(name: 'Samwise', title: 'the Brave', race: 'Halfling', gender: 'Male', age: 36, heroClass: 'Bard').save(flush: true, failOnError: true)
        new Hero(name: 'Aragorn', title: 'Elessar', race: 'Dwarf', gender: 'Male', age: 88, heroClass: 'Ranger').save(flush: true, failOnError: true)
        new Hero(name: 'Gimli', title: 'Elf Friend', race: 'Dwarf', gender: 'Male', age: 140, heroClass: 'Barbarian').save(flush: true, failOnError: true)
        new Hero(name: 'Legolas', title: null, race: 'Elf', gender: 'Male', age: 800, heroClass: 'Ranger').save(flush: true, failOnError: true)
        new Hero(name: 'Arwen', title: null, race: 'Dwarf', gender: 'Female', age: 2778, heroClass: 'Ranger').save(flush: true, failOnError: true)
        hero.id
    }

    void "test get"() {
        setupData()

        expect:
        heroService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Hero> heroList = heroService.list(max: 2, offset: 2)

        then:
        heroList.size() == 2
        heroList.get(0).name == 'Gimli'
        heroList.get(1).name == 'Legolas'
    }

    void "test count"() {
        setupData()

        expect:
        heroService.count() == 5
    }

    void "test delete"() {
        Long heroId = setupData()

        expect:
        heroService.count() == 5

        when:
        heroService.delete(heroId)
        sessionFactory.currentSession.flush()

        then:
        heroService.count() == 4
    }

    void "test save"() {
        when:
        Hero hero =  new Hero(name: 'Theoden', title: 'Horsemaster', race: 'Human', gender: 'Male', age: 71, heroClass: 'Paladin')
        heroService.save(hero)

        then:
        hero.id != null
    }
}
