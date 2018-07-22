package herorepo

class Hero {
    String name
    String title
    String race
    String gender
    int age
    String heroClass

    static constraints = {
        name unique: true, blank: false, minSize: 3, maxSize: 32, matches: "[a-zA-Z]+"
        title nullable: true, minSize: 3, maxSize: 32
        race inList: ["Dwarf", "Elf", "Halfling", "Human", "Orc"]
        gender inList: ["Female", "Male"]
        age min: 1
        heroClass inList: ["Barbarian", "Bard", "Druid", "Monk", "Paladin", "Ranger", "Sorcerer", "Warlock"]
    }
}
