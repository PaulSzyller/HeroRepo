package herorepo

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class HeroSpec extends Specification {

    def hero

    def setup() {
        hero = new Hero(name: 'FooBar', title: null, race: 'Dwarf', gender: 'Female', age: 1, heroClass: 'Barbarian')
    }

    def cleanup() {
    }

    void "name can only contain alpha characters"() {
        when:
        hero.name = "123 456"

        then:
        !hero.validate(['name'])
    }

    void "name must be unique"() {
        when:
        hero.save()
        Hero hero2 = new Hero(name: 'FooBar', title: null, race: 'Human', gender: 'Male', age: 2, heroClass: 'Bard')

        then:
        !hero2.validate(['name'])
    }

    void "race must be one of approved inputs"() {
        when:
        hero.race = "notavalidrace"

        then:
        !hero.validate(['race'])
    }

    void "gender must be one of approved inputs"() {
        when:
        hero.gender = "notavalidgender"

        then:
        !hero.validate(['gender'])
    }

    void "age must be greater than 1"() {
        when:
        hero.age = -3

        then:
        !hero.validate(['age'])
    }

    void "heroClass must be one of approved inputs"() {
        when:
        hero.heroClass = "notavalidclass"

        then:
        !hero.validate(['heroClass'])
    }
}
