package me.ezeh.language

import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import java.util.*


class Util {
    companion object {


        fun findAllPackagesStartingWith(prefix: String): Set<String> {
            val classLoadersList = LinkedList<ClassLoader>()
            classLoadersList.add(ClasspathHelper.contextClassLoader())
            classLoadersList.add(ClasspathHelper.staticClassLoader())
            val reflections = Reflections(ConfigurationBuilder()
                    .setScanners(SubTypesScanner(false), ResourcesScanner())
                    .setUrls(ClasspathHelper.forClassLoader(*classLoadersList.toTypedArray()))
                    .filterInputsBy(FilterBuilder().include(FilterBuilder.prefix("my.base.package"))))
            val classes = reflections.getSubTypesOf(Any::class.java)

            val packageNameSet = TreeSet<String>()
            for (classInstance in classes) {
                val packageName = classInstance.`package`.name
                if (packageName.startsWith(prefix)) {
                    packageNameSet.add(packageName)
                }
            }
            return packageNameSet
        }
    }
}