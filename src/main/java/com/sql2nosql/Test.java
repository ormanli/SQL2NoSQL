package com.sql2nosql;

import com.sql2nosql.node.NodeAction;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Test implements Constants {
    public static void main(String[] args) throws Exception {
        Class[] aa = getClasses(ActionPackage);
        List queue = new ArrayList();
        for (int i = 0; i < aa.length; i++) {
            if (!aa[i].isInterface())
                queue.add(Class.forName(aa[i].getName()).newInstance());

        }
        for (int i = 0; i < queue.size(); i++) {
            NodeAction action = (NodeAction) queue.get(i);
        }
        int a = 0;
    }

    private static Set<Class> getClassesInPackage(String packageName) {
        Set<Class> classes = new HashSet<Class>();
        String packageNameSlashed = "/" + packageName.replace(".", "/");
        // Get a File object for the package
        URL directoryURL = Thread.currentThread().getContextClassLoader().getResource(packageNameSlashed);
        if (directoryURL == null) {
            System.err.println("Could not retrieve URL resource: " + packageNameSlashed);
            return classes;
        }

        String directoryString = directoryURL.getFile();
        if (directoryString == null) {
            System.err.println("Could not find directory for URL resource: " + packageNameSlashed);
            return classes;
        }

        File directory = new File(directoryString);
        if (directory.exists()) {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (String fileName : files) {
                // We are only interested in .class files
                if (fileName.endsWith(".class")) {
                    // Remove the .class extension
                    fileName = fileName.substring(0, fileName.length() - 6);
                    try {
                        classes.add(Class.forName(packageName + "." + fileName));
                    } catch (ClassNotFoundException e) {
                        System.err.println(packageName + "." + fileName + " does not appear to be a valid class.");
                    }
                }
            }
        } else {
            System.err.println(packageName + " does not appear to exist as a valid package on the file system.");
        }
        return classes;
    }


    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }


}
