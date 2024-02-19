plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.annotation:annotation:1.7.1")
    testImplementation("junit:junit:4.12")
    testImplementation(project(":app"))
    testImplementation(project(":app"))
}