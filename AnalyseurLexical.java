

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class AnalyseurLexical {
    private ArrayList<Character> fluxCaracteres;  
    private int indiceCourant;                     
    private char caractereCourant;                 
    private boolean eof;                           
   
    public AnalyseurLexical() {
        this("");
    }
    
    public AnalyseurLexical(String nomFichier) {
        BufferedReader f = null;
        int car = 0;
        fluxCaracteres = new ArrayList<Character>();
        indiceCourant = 0;
        eof = false;
        
        try {
            f = new BufferedReader(new FileReader(nomFichier));
        } catch(IOException e) {
            System.out.println("Tapez votre code (Ctrl+D pour finir sous Linux, Ctrl+Z sous Windows):");
            f = new BufferedReader(new InputStreamReader(System.in));
        }
        
        
        try {
            while((car = f.read()) != -1)
                fluxCaracteres.add((char)car);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void caractereSuivant() {
        if(indiceCourant < fluxCaracteres.size())
            caractereCourant = fluxCaracteres.get(indiceCourant++);
        else
            eof = true;
    }
    
    public void reculer() {
        if(indiceCourant > 0)
            indiceCourant--;
    }
    
    
    public UniteLexicale lexemeSuivant() {
        caractereSuivant();
        
        
        while(!eof && Character.isWhitespace(caractereCourant)) {
            caractereSuivant();
        }
        
        
        if (eof)
            return new UniteLexicale(Categorie.EOF, "EOF");
        
        
        if(Character.isLetter(caractereCourant))
            return getIdentifiantOuMotCle();
        
        
        if(Character.isDigit(caractereCourant))
            return getNombre();
        
        
        if(caractereCourant == ':')
            return getAffectation();
        
        
        switch(caractereCourant) {
            case '+': return new UniteLexicale(Categorie.PLUS, "+");
            case '-': return new UniteLexicale(Categorie.MOINS, "-");
            case '*': return new UniteLexicale(Categorie.MULT, "*");
            case '/': return new UniteLexicale(Categorie.DIV, "/");
            case '(': return new UniteLexicale(Categorie.PARO, "(");
            case ')': return new UniteLexicale(Categorie.PARF, ")");
            case '{': return new UniteLexicale(Categorie.ACCO, "{");
            case '}': return new UniteLexicale(Categorie.ACCF, "}");
            case ';': return new UniteLexicale(Categorie.PV, ";");
        }
        
        
        System.err.println("Erreur lexicale: caractère non reconnu '" + caractereCourant + "'");
        return null;
    }
    
    
    public UniteLexicale getIdentifiantOuMotCle() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        
        while(true) {
            switch(etat) {
                case 0: 
                    
                    etat = 1;
                    sb.append(caractereCourant);
                    break;
                    
                case 1:
                    
                    caractereSuivant();
                    if(eof)
                        etat = 3;
                    else if(Character.isLetterOrDigit(caractereCourant))
                        sb.append(caractereCourant);
                    else
                        etat = 2;
                    break;
                    
                case 2:
                    
                    reculer();
                    return classerMotCle(sb.toString());
                    
                case 3:
                    
                    return classerMotCle(sb.toString());
            }
        }
    }
    
    private UniteLexicale classerMotCle(String mot) {
        switch(mot) {
            case "if":   return new UniteLexicale(Categorie.IF, "if");
            case "then": return new UniteLexicale(Categorie.THEN, "then");
            case "else": return new UniteLexicale(Categorie.ELSE, "else");
            default:     return new UniteLexicale(Categorie.ID, mot);
        }
    }
    
    
    public UniteLexicale getNombre() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        
        while(true) {
            switch(etat) {
                case 0:
                    
                    etat = 1;
                    sb.append(caractereCourant);
                    break;
                    
                case 1:
                    
                    caractereSuivant();
                    if(eof)
                        etat = 3;
                    else if(Character.isDigit(caractereCourant))
                        sb.append(caractereCourant);
                    else
                        etat = 2;
                    break;
                    
                case 2:
                    
                    reculer();
                    return new UniteLexicale(Categorie.NOMBRE, sb.toString());
                    
                case 3:
                    
                    return new UniteLexicale(Categorie.NOMBRE, sb.toString());
            }
        }
    }


    public UniteLexicale getAffectation() {
        int etat = 0;
        StringBuffer sb = new StringBuffer();
        
        while(true) {
            switch(etat) {
                case 0:
                    
                    sb.append(caractereCourant);
                    caractereSuivant();
                    etat = 1;
                    break;
                    
                case 1:
                    
                    if(!eof && caractereCourant == '=') {
                        sb.append(caractereCourant);
                        return new UniteLexicale(Categorie.AFFECT, ":=");
                    } else {
                        
                        System.err.println("Erreur lexicale: ':' doit être suivi de '='");
                        reculer();
                        return null;
                    }
            }
        }
    }
    
    
    public String toString() {
        return fluxCaracteres.toString();
    }
}
