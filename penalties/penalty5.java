// Запустите код лямбды r в отдельном потоке при помощи класса Thread:
// public class newJavaFile {
//     public static void main(String args[]) {
//         Runnable r = () -> {
//             System.out.println("Hello, world!");
//         };
//         /* ... */
//     }
// }

public class penalty5 {
    public static void main(String args[]) {
        Runnable r = () -> {
            System.out.println("Hello, world!");
        };

        Thread thread = new Thread(r);
        thread.start();
        /* ... */
    }
}

