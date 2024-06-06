rootProject.name = "CatchKU"
include(
    ":app",
    ":unityLibrary",
    ":unityLibrary:xrmanifest.androidlib",
)
project(":unityLibrary").projectDir=File("./unityLibrary")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://repository.map.naver.com/archive/maven")
        }
        /**
         * unityLibrary/libs 하위에 유니티 관련 aar 파일들을 unityLibrary gradle에서 implementation 하기 위해 필요하다
         * */
        flatDir {
            dirs("${project(":unityLibrary").projectDir}/libs")
        }
    }
}
