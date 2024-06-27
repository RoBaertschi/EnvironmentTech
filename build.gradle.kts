import java.net.URI

plugins {
	`java-library`
	eclipse
	idea
	`maven-publish`
	id("io.freefair.lombok") version "8.6"
	id ("net.neoforged.gradle.userdev") version ("7.0.151")
	id("com.diffplug.spotless") version "7.0.0.BETA1"
}

val minecraftVersion: String by project
val minecraftVersionRange: String by project
val neoVersion: String by project
val neoVersionRange: String by project
val loaderVersionRange: String by project

val modId: String by project
val modName: String by project
val modLicense: String by project
val modVersion: String by project
val modGroupId: String by project
val modAuthors: String by project
val modDescription: String by project

val junitVersion: String by project
val assertjVersion: String by project
val topVersion: String by project
val reiVersion: String by project
val jeiVersion: String by project
val jeiMcVersion: String by project
val emiVersion: String by project

val withTop = true

version = modVersion
group = modGroupId

repositories {
	mavenLocal()
	maven {
		url = URI.create("https://maven.k-4u.nl")
	}
	maven {
		url = URI.create("https://maven.blamejared.com")
		name = "BlameJared"
	}

	maven {
		url = URI.create("https://maven.shedaniel.me/")
		name = "Shedaniel"
	}
	maven {
		url = URI.create("https://maven.architectury.dev/")
		name = "Architectury"
	}
	maven {
		url = URI.create("https://maven.terraformersmc.com/")
		name = "TerraformersMC"
	}
}

base {
	archivesName = modId
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

spotless {
	format("misc") {
		target("*.gradle.kts", ".gitattributes", ".gitignore")

		trimTrailingWhitespace()
		indentWithTabs()
		endWithNewline()
	}

	java {
		// Originally, this was from Kaupenjoe's repo, and was licensed by BluSunrize as the original code was from her, so we exclude it to not add our License Header
		targetExclude("src/main/java/robaertschi/environmenttech/client/renderer/EnvStorageRenderer.java",
			"src/main/java/robaertschi/environmenttech/utils/MouseUtils.java")
		importOrder("lombok", "java|javax", "", "net.minecraft", "com.mojang", "robaertschi", "\\#")
		removeUnusedImports()
		licenseHeaderFile("HEADER.java")
	}
}

sourceSets {
	// Include resources generated by data generators.
	main.configure {
		resources {
			srcDir("src/generated/resources")
		}
	}
	create("junit") {
		java {
		}

		compileClasspath += sourceSets.main.get().output
		runtimeClasspath += sourceSets.main.get().output

	}


}


val junitImplementation: Configuration by configurations.getting {
	extendsFrom(configurations.implementation.get())
}
val junitRuntimeOnly: Configuration by configurations.getting {
	extendsFrom(configurations.runtimeOnly.get())
}



configurations["junitImplementation"].extendsFrom(configurations.implementation.get())




//minecraft.accessTransformers.file rootProject.file("src/main/resources/META-INF/accesstransformer.cfg")
//minecraft.accessTransformers.entry public net.minecraft.client.Minecraft textureManager # textureManager


runs {

	configureEach {
		// Recommended logging data for an userdev environment
		// The markers can be added/remove as needed separated by commas.
		// "SCAN": For mods scan.
		// "REGISTRIES": For firing of registry events.
		// "REGISTRYDUMP": For getting the contents of all registries.
		systemProperty ("forge.logging.markers", "REGISTRIES")

		systemProperty ("forge.logging.console.level", "debug")

		modSource (project.sourceSets["main"])
	}

	create("junit") {
		junit(true)

		unitTestSources(sourceSets["junit"])
	}

	create("client") {
		// Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
		systemProperty ("forge.enabledGameTestNamespaces", modId)
	}

	create("server") {
		systemProperty ("forge.enabledGameTestNamespaces", modId)
		programArgument ("--nogui")
	}

	// This run config launches GameTestServer and runs all registered gametests, then exits.
	// By default, the server will crash when no gametests are provided.
	// The gametest system is also enabled by default for other run configs under the /test command.
	create("gameTestServer") {
		systemProperty ("forge.enabledGameTestNamespaces", modId)

	}


	create("data") {
		// example of overriding the workingDirectory set in configureEach above, uncomment if you want to use it
		// workingDirectory project.file("run-data")

		// Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
		programArguments.addAll ("--mod", modId, "--all", "--output", file("src/generated/resources/").absolutePath, "--existing", file("src/main/resources/").absolutePath)
	}

}

dependencies {
	implementation ("net.neoforged:neoforge:${neoVersion}")

	compileOnly("mcjty.theoneprobe:theoneprobe:${topVersion}")
	if (withTop)
		runtimeOnly("mcjty.theoneprobe:theoneprobe:${topVersion}")
//    compileOnly("dev.emi:emi-neoforge:${emiVersion}:api")
//    runtimeOnly("dev.emi:emi-neoforge:${emiVersion}")
//    runtimeOnly("me.shedaniel:RoughlyEnoughItems-neoforge:${reiVersion}")
//    compileOnly("me.shedaniel:RoughlyEnoughItems-api-neoforge:${reiVersion}")
//    compileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin-neoforge:${reiVersion}")


	// Testing
	junitImplementation(platform("org.junit:junit-bom:${junitVersion}"))
	junitImplementation("org.junit.jupiter:junit-jupiter-params")
	junitRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

	junitImplementation("org.assertj:assertj-core:${assertjVersion}")

	// Example mod dependency with JEI
	// The JEI API is declared for compile time use, while the full JEI artifact is used at runtime
	compileOnly("mezz.jei:jei-${jeiMcVersion}-common-api:${jeiVersion}")
	compileOnly("mezz.jei:jei-${jeiMcVersion}-neoforge-api:${jeiVersion}")
	runtimeOnly("mezz.jei:jei-${jeiMcVersion}-neoforge:${jeiVersion}")

	// Example mod dependency using a mod jar from ./libs with a flat dir repository
	// This maps to ./libs/coolmod-${mc_version}-${coolmod_version}.jar
	// The group id is ignored when searching -- in this case, it is "blank"
	// implementation "blank:coolmod-${mc_version}:${coolmod_version}"

	// Example mod dependency using a file as dependency
	// implementation files("libs/coolmod-${mc_version}-${coolmod_version}.jar")

	// Example project dependency using a sister or child project:
	// implementation project(":myproject")

	// For more info:
	// http://www.gradle.org/docs/current/userguide/artifact_dependencies_tutorial.html
	// http://www.gradle.org/docs/current/userguide/dependency_management.html
}


afterEvaluate {
//    runs["junit"].modSources(runs["junit"].modSources)
//    runs["junit"].modSources = runs["junit"].modSources.get().stream().filter { it != sourceSets.main.get() }.toList()
	runs["junit"].modSources.all().get().values().remove(sourceSets.main.get())
}



// This block of code expands all declared replace properties in the specified resource targets.
// A missing property will result in an error. Properties are expanded using ${} Groovy notation.
// When "copyIdeResources" is enabled, this will also run before the game launches in IDE environments.
// See https://docs.gradle.org/current/dsl/org.gradle.language.jvm.tasks.ProcessResources.html
tasks.withType<ProcessResources>().configureEach {
	val replaceProperties = mapOf(
			"minecraft_version"      to minecraftVersion,
			"minecraft_version_range" to minecraftVersionRange,
			"neo_version"            to neoVersion,
			"neo_version_range"      to neoVersionRange,
			"loader_version_range"   to loaderVersionRange,
			"mod_id"                 to modId,
			"mod_name"               to modName,
			"mod_license"            to modLicense,
			"mod_version"            to modVersion,
			"mod_authors"            to modAuthors,
			"mod_description"        to modDescription)
	inputs.properties(replaceProperties)

	filesMatching("META-INF/neoforge.mods.toml") {
		expand(replaceProperties)
	}

}

// Example configuration to allow publishing using the maven-publish plugin
publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			from (components["java"])
		}
	}
	repositories {

		maven {
			url = project.projectDir.toURI()
		}
	}
}

// IDEA no longer automatically downloads sources/javadoc jars for dependencies, so we need to explicitly enable the behavior.
idea {
	module {
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}

tasks.test {
	useJUnitPlatform()
}
