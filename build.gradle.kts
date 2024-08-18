plugins {
    id("java")
}

//应用特定插件
apply(plugin = "chocolate_gradle")

//导入插件的任务
buildscript{
    repositories{
        //maven {url = uri("E:\\Project\\chocolate_gradle\\repo")}
        maven { url = uri("https://www.jitpack.io") }
    }
    dependencies {
        classpath("com.github.howxu:chocolate_gradle:v1.4")
    }
}


//UTF8中文支持
tasks.withType<JavaCompile>{
    options.encoding="UTF-8"
}

group = "cn.howxu.chocolate"
version = "2.2"

repositories {
    mavenCentral()
    //tv.twitch在这找到
    maven { url = uri("https://nexus.velocitypowered.com/repository/maven-public/")}
    //Mojang啥时候开了个maven库
    maven { url = uri("https://libraries.minecraft.net/")}
}

//println(extensions.getByName("chocolate"))

//TODO Why I can't use extension?