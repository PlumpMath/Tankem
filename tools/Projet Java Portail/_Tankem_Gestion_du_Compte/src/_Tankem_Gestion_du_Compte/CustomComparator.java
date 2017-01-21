package _Tankem_Gestion_du_Compte;

import java.util.Comparator;

public class CustomComparator implements Comparator<Partie> {
    @Override
    public int compare(Partie o1, Partie o2) {
        return o1.getLaDate().compareTo(o2.getLaDate());
    }
}
