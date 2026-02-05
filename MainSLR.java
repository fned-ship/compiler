

import java.util.ArrayList;


public class MainSLR {
    
    
    public static String[] convertirTokensEnSymboles(AnalyseurLexical analyseur) {
        ArrayList<String> symboles = new ArrayList<>();
        UniteLexicale token;
        
        do {
            token = analyseur.lexemeSuivant();
            if(token != null && token.getCategorie() != Categorie.EOF) {
                
                String symbole = categorieVersSymbole(token.getCategorie());
                if(symbole != null) {
                    symboles.add(symbole);
                }
            }
        } while(token != null && token.getCategorie() != Categorie.EOF);
        
        
        symboles.add("$");
        
        return symboles.toArray(new String[0]);
    }
    
    private static String categorieVersSymbole(Categorie cat) {
        switch(cat) {
            case ID:     return "id";
            case NOMBRE: return "nombre";
            case IF:     return "if";
            case THEN:   return "then";
            case ELSE:   return "else";
            case AFFECT: return ":=";
            case PLUS:   return "+";
            case MOINS:  return "-";
            case MULT:   return "*";
            case DIV:    return "/";
            case PARO:   return "(";
            case PARF:   return ")";
            case ACCO:   return "{";
            case ACCF:   return "}";
            case PV:     return ";";
            case EOF:    return "$";
            default:     return null;
        }
    }
    
    
    public static void testerAnalyseLexicale(String nomFichier) {
        System.out.println("ANALYSE LEXICALE");
        
        AnalyseurLexical analyseur = new AnalyseurLexical(nomFichier);
        ArrayList<UniteLexicale> tokens = new ArrayList<>();
        
        UniteLexicale unite;
        do {
            unite = analyseur.lexemeSuivant();
            if(unite != null) {
                tokens.add(unite);
                System.out.println(unite);
            }
        } while(unite != null && unite.getCategorie() != Categorie.EOF);
        
        System.out.println("\nNombre total de tokens: " + tokens.size());
    }
    
    public static void testerCompilateurSLR(String nomFichier) {
        System.out.println("Phase 1: ANALYSE LEXICALE ");
        
        AnalyseurLexical analyseurLex = new AnalyseurLexical(nomFichier);
        
        
        ArrayList<UniteLexicale> listeTokens = new ArrayList<>();
        UniteLexicale token;
        do {
            token = analyseurLex.lexemeSuivant();
            if(token != null) {
                listeTokens.add(token);
                System.out.println("  Token: " + token);
            }
        } while(token != null && token.getCategorie() != Categorie.EOF);
        
        
        ArrayList<String> symboles = new ArrayList<>();
        for(UniteLexicale t : listeTokens) {
            if(t.getCategorie() != Categorie.EOF) {
                String symbole = categorieVersSymbole(t.getCategorie());
                if(symbole != null) {
                    symboles.add(symbole);
                    System.out.println("  " + t.getCategorie() + " → " + symbole);
                }
            }
        }
        
        System.out.println("Phase 2: ANALYSE SYNTAXIQUE SLR");
        
        AnalyseurSLR analyseurSLR = new AnalyseurSLR();
        analyseurSLR.analyser(symboles.toArray(new String[0]));
    }
    
    
    
    public static void main(String[] args) {
            String fichier = "src/exemple.txt";
            testerCompilateurSLR(fichier);
        
    }
}
