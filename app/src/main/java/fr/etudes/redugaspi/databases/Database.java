package fr.etudes.redugaspi.databases;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductCourses;
import fr.etudes.redugaspi.models.ProductName;

public class Database<T> {
    private final List<T> data = new ArrayList<>();
    private final String basename;
    private String filename;

    private static Database<Product> products = new Database<>("products.dat");
    private static Database<ProductName> names = new Database<>("names.dat");
    private static Database<Product> history = new Database<>("history.dat");
    private static Database<ProductCourses> courses = new Database<>("courses.dat");

    public static void setContextAll(Context context) {
        products.setContext(context);
        names.setContext(context);
        history.setContext(context);
        courses.setContext(context);
    }

    public static void LoadAll() {
        products.load();
        names.load();
        history.load();
        courses.load();
    }

    public static Database<Product> getProducts() {
        return products;
    }

    public static Database<ProductName> getNames() {
        return names;
    }

    public static Database<Product> getHistory() {
        return history;
    }

    public static Database<ProductCourses> getCourses() {
        return courses;
    }

    public static void SaveAll() {
        products.save();
        names.save();
        history.save();
        courses.save();
    }

    public static void ClearAll(Context context) {
        products.clear(context);
        names.clear(context);
        history.clear(context);
        courses.clear(context);
    }

    private Database(String filename) {
        this.basename = filename;
    }

    public void setContext(Context context) {
        filename = context.getCacheDir().getAbsolutePath()+"/"+basename;
    }

    public void clear(Context context) {
        SerializableManager.removeSerializable(context, filename);
    }

    public void load() {
        data.clear();
        T[] array = (T[]) SerializableManager.readSerializable(filename);
        if (array != null)
            data.addAll(Arrays.asList(array));
    }

    public void save() {
        SerializableManager.saveSerializable( data.toArray(), filename);
    }

    public List<T> getAll() {
        load();
        return data;
    }

    public T getFirst(Predicate<T> filter) {
        List<T> allMatching = get(filter);
        if (allMatching.size() > 0) return allMatching.get(0);
        return null;
    }

    public List<T> get(Predicate<? super T> filter) {
        return getAll().stream().filter(filter).collect(Collectors.toList());
    }

    public void add(T object) {
        load();
        data.add(object);
        save();
    }

    public void remove(T object) {
        load();
        data.remove(object);
        save();
    }
}
