package fr.etudes.redugaspi.databases;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductName;

public class Database<T> {
    private final List<T> data = new ArrayList<>();
    private final String filename;

    private static Database<Product> products = new Database<>("products.dat");;
    private static Database<ProductName> names = new Database<>("names.dat");;
    private static Database<Product> history = new Database<>("history.dat");;

    public static void LoadAll(Context context) {
        products.load(context);
        names.load(context);
        history.load(context);
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

    public static void SaveAll(Context context) {
        products.save(context);
        names.save(context);
        history.save(context);
    }

    public static void ClearAll(Context context) {
        products.clear(context);
        names.clear(context);
        history.clear(context);
    }

    private Database(String filename) {
        this.filename = filename;
    }

    public void clear(Context context) {
        SerializableManager.removeSerializable(context, filename);
    }

    public void load(Context context) {
        data.clear();
        T[] array = (T[]) SerializableManager.readSerializable( context, filename);
        if (array != null)
            data.addAll(Arrays.asList(array));
    }

    public void save(Context context) {
        SerializableManager.saveSerializable(context, data.toArray(), filename);
    }

    public List<T> getAll() {
        return data;
    }

    public T getFirst(Predicate<T> filter) {
        List<T> allMatching = get(filter);
        if (allMatching.size() > 0) return allMatching.get(0);
        return null;
    }

    public List<T> get(Predicate<? super T> filter) {
        return data.stream().filter(filter).collect(Collectors.toList());
    }

    public void add(Context context, T object) {
        data.add(object);
        save(context);
    }
}
