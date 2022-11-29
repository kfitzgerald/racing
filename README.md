# racing
https://www.spigotmc.org/resources/%EF%B8%8Fracing.69086/

## Dependencies
- https://github.com/PaperMC/Paper
- https://github.com/PaperMC/PaperLib
- https://github.com/dmulloy2/ProtocolLib/
- https://github.com/filoghost/HolographicDisplays
- https://github.com/Bastian/bStats-Metrics
- https://github.com/MilkBowl/VaultAPI
- https://github.com/TrollskogenMC/commando [^1]
- https://github.com/TrollskogenMC/versioned-config [^1]
- https://github.com/TrollskogenMC/messenger [^1]
- https://github.com/koca2000/NoteBlockAPI
- https://github.com/mcMMO-Dev/mcMMO [^2]
- https://github.com/DV8FromTheWorld/JDA

[^1]Because commando, versioned-config and messenger are hosted on GitHub packages you will need to [setup GitHub Authentication for Maven](https://docs.github.com/en/free-pro-team@latest/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages).

[^2]mcMMO requires building and installing in your local maven repository. See [this issue](https://github.com/mcMMO-Dev/mcMMO/issues/4181).


~/Downloads/apache-maven-3.8.2/bin/mvn install:install-file -Dfile=lib/versioned-config-1.0-20200731.171358-1.jar -DgroupId=se.hornta -DartifactId=versioned-config -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
~/Downloads/apache-maven-3.8.2/bin/mvn install:install-file -Dfile=lib/messenger-1.0-20200731.172243-1.jar -DgroupId=se.hornta -DartifactId=messenger -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
~/Downloads/apache-maven-3.8.2/bin/mvn install:install-file -Dfile=lib/commando-1.0-20200731.172157-1.jar -DgroupId=se.hornta -DartifactId=commando -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
