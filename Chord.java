import javax.swing.*;
import java.util.*;
import java.awt.Image;

public class Chord {
   
    public enum ROOT {
        C(0),
        Cs(1), 
        Db(2),
        D(3),
        Ds(4),
        Eb(5),
        E(6), 
        F(7), 
        Fs(8), 
        Gb(9),
        G(10), 
        Gs(11), 
        Ab(12),
        A(13), 
        As(14), 
        Bb(15),
        B(16);

        public final int root_num;
        private ROOT(int root_num) {
            this.root_num = root_num;
        }

        private static final List<ROOT> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final Random random = new Random();
        public static ROOT randomRoot()  {
            return VALUES.get(random.nextInt(VALUES.size()));
        }
    }

    public enum TYPE {
        MAJ(0), 
        MIN(1), 
        DIM(2),  
        AUG(3),
        DOM7(4), 
        DIM7(5);

        public final int type_num;
        private TYPE(int type_num) {
            this.type_num = type_num;
        }

        private static final List<TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final Random random = new Random();
        public static TYPE randomType()  {
            return VALUES.get(random.nextInt(VALUES.size()));
        }
    }

    public final ROOT r;
    public final TYPE t;

    public Chord(ROOT r, TYPE t) {
        this.r = r;
        this.t = t;
    }

    public static Chord randomChord() {
        return new Chord(ROOT.randomRoot(), TYPE.randomType());
    }

    public ImageIcon getIcon() {
        ImageIcon icon = new ImageIcon("Chord_Icons/" + getName().replaceAll(" ", "_") + ".png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(284, 192,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        return icon;
    }

    public String getName() {
        String str = "";
        if (r.toString().length() == 2 && r.toString().charAt(1) == 's')
            str += r.toString().charAt(0) + "#";
        else
            str += r.toString();
        str += " ";
        switch (t.type_num) {
            case 0:
                str += "Maj";
                break;
            case 1:
                str += "min";
                break;
            case 2:
                str += "dim";
                break;
            case 3:
                str += "aug";
                break;
            case 4:
                str += "Dom7";
                break;  
            case 5:
                str += "dim7";
                break;
        }
        return str;
    }

    public boolean equals(Chord c) {
        return (c.r == r && c.t == t);
    }

}
