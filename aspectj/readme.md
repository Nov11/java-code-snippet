* commenting out maven-compiler-plugin helps
* if maven-compiler-plugins show out later, aspectj comes in first, it won't work
* if aspect class in target folder doesn't have hasAspect/aspectOf method, it fails(NoSuchMethodException)
* parent pom has a maven-compiler-plugin config, I'm not sure how this influence the plugin execution sequence