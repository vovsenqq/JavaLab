// В методе main() замените суммирование в цикле на stream+lambda:

// import java.util.*;

// public class newJavaFile {
//     public static void main(String args[]) {
//         innerClass[] array = new innerClass[100];
//         /* ... */
//         long sum = 0;
//         for (innerClass ic : array) {
//             sum += ic.getValue();
//         }
        
//     }

//     static class innerClass {
//         private long a = 0;
//         innerClass() {
//             /* ... */
//         }
//         long getValue() { return a; }
//     }

// }

import java.util.*;
import java.util.stream.*;

public class penalty3 {
    public static void main(String args[]) {
        innerClass[] array = new innerClass[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = new innerClass();
        }
        long sum = Arrays.stream(array)
                         .mapToLong(innerClass::getValue)
                         .sum();
        System.out.println(sum);
    }

    static class innerClass {
        private long a = 1;
        innerClass() {
            /* ... */
        }
        long getValue() { return a; }
    }
}


