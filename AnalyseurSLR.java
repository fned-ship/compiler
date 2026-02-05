

import java.util.Stack;
import java.util.ArrayList;


public class AnalyseurSLR {
    
    private String[] regles = {
        "S'->S",           
        "S->id := E",      
        "S->if E then S else S",  
        "S->{ L }",        
        "L->S",            
        "L->S ; L",        
        "E->E + T",
        "E->E - T", 
        "E->T",      
        "T->T * F", 
        "T->T / F",  
        "T->F",      
        "F->( E )",        
        "F->id",           
        "F->nombre"        
    };
    
   
    private String[][] tableSLR = {
        
        { "id", ":=", "if", "then", "else", "{", "}", ";", "+", "-", "*", "/", "(", ")", "nombre", "$", "S", "L", "E", "T", "F"},
        {"s2", ""  , "s3", ""  , ""  , "s4", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "1" , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "ACC", "" , ""  , ""  , ""  , ""  },
             {""  , "s5", ""  , ""  , "r13", "" , "r13", "r13", "r13", "r13", "r13", "r13", ""  , "r13", ""  , "r13", "" , ""  , ""  , ""  , ""  },
             {"s12", "" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "s11", "" , "s13", ""  , ""  , ""  , "6", "7", "8" },
             {"s2", ""  , "s3", ""  , ""  , "s4", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "9", "10", ""  , ""  , ""  },
             {"s12", "" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "s11", "" , "s13", ""  , ""  , ""  , "14", "7", "8" },
             {""  , ""  , ""  , "s15", ""  , ""  , ""  , ""  , "s16", "s17", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r8" , "r8" , ""  , "r8" , "r8" , "r8" , "r8" , "s18", "s19", ""  , "r8" , ""  , "r8" , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r11", "r11", ""  , "r11", "r11", "r11", "r11", "r11", "r11", ""  , "r11", ""  , "r11", ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , ""  , ""  , ""  , "r4" , "s20", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , ""  , ""  , ""  , "s21", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  },
             {"s12", "" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "s11", "" , "s13", ""  , ""  , ""  , "22", "7", "8" },
             {""  , ""  , ""  , "r13", "r13", ""  , "r13", "r13", "r13", "r13", "r13", "r13", ""  , "r13", ""  , "r13", ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r14", "r14", ""  , "r14", "r14", "r14", "r14", "r14", "r14", ""  , "r14", ""  , "r14", ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r1" , "r1" , ""  , "r1" , "r1" , "s16", "s17", ""  , ""  , ""  , "r1" , ""  , "r1" , ""  , ""  , ""  , ""  , ""  },
             {"s2", ""  , "s3", ""  , ""  , "s4", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "23", ""  , ""  , ""  , ""  },
             {"s12", "" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "s11", "" , "s13", ""  , ""  , ""  , ""  , "24", "8" },
             {"s12", "" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "s11", "" , "s13", ""  , ""  , ""  , ""  , "25", "8" },
             {"s12", "" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "s11", "" , "s13", ""  , ""  , ""  , ""  , ""  , "26"},
             {"s12", "" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "s11", "" , "s13", ""  , ""  , ""  , ""  , ""  , "27"},
             {"s2", ""  , "s3", ""  , ""  , "s4", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "9" , "28", ""  , ""  , ""  },
             {""  , ""  , ""  , ""  , "r3" , ""  , "r3" , "r3" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "r3" , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "s16", "s17", ""  , ""  , ""  , "s29", ""  , ""  , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , ""  , "s30", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r6" , "r6" , ""  , "r6" , "r6" , "r6" , "r6" , "s18", "s19", ""  , "r6" , ""  , "r6" , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r7" , "r7" , ""  , "r7" , "r7" , "r7" , "r7" , "s18", "s19", ""  , "r7" , ""  , "r7" , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r9" , "r9" , ""  , "r9" , "r9" , "r9" , "r9" , "r9" , "r9" , ""  , "r9" , ""  , "r9" , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r10", "r10", ""  , "r10", "r10", "r10", "r10", "r10", "r10", ""  , "r10", ""  , "r10", ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , ""  , ""  , ""  , "r5" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , "r12", "r12", ""  , "r12", "r12", "r12", "r12", "r12", "r12", ""  , "r12", ""  , "r12", ""  , ""  , ""  , ""  , ""  },
             {"s2", ""  , "s3", ""  , ""  , "s4", ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "31", ""  , ""  , ""  , ""  },
             {""  , ""  , ""  , ""  , "r2" , ""  , "r2" , "r2" , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "r2" , ""  , ""  , ""  , ""  , ""  }};
    
    private Stack<String> pile;              
    private ArrayList<String> tokens;         
    private int index;                        
    private String action;                    
    
    
    public AnalyseurSLR() {
        pile = new Stack<>();
        tokens = new ArrayList<>();
        index = 0;
        action = "";
    }
    
    
    public void analyser(String[] tokensInput) {
        
        pile.clear();
        tokens.clear();
        index = 0;
        action = "";
        
        
        for(String token : tokensInput) {
            tokens.add(token);
        }
        
        if(tokens.isEmpty() || !tokens.get(tokens.size()-1).equals("$")) {
            tokens.add("$");
        }
        
        
        pile.push("0");
        
        System.out.println("ANALYSE SYNTAXIQUE SLR " );
        System.out.println("\n│        PILE               │       ENTRÉE        │       ACTION       │");
        
        afficher();
        
        
        while(index < tokens.size()) {
            String etatCourant = pile.peek();
            String symboleCourant = tokens.get(index);
            
            String actionTable = obtenirAction(etatCourant, symboleCourant);
            
            if(actionTable.startsWith("s")) {
                
                int nouvelEtat = Integer.parseInt(actionTable.substring(1));
                pile.push(symboleCourant);
                pile.push(String.valueOf(nouvelEtat));
                index++;
                action = "shift " + nouvelEtat;
                afficher();
                
            } else if(actionTable.startsWith("r")) {
                
                int numeroRegle = Integer.parseInt(actionTable.substring(1));
                String regle = regles[numeroRegle];
                
                
                String[] parties = regle.split("->");
                String partieGauche = parties[0];
                String partieDroite = parties[1];
                
                String[] symboles = partieDroite.split(" ");
                int nbSymboles=symboles.length;;
                
                
                
                for(int i = 0; i < 2 * nbSymboles; i++) {
                    pile.pop();
                }
                
                
                String etatSommet = pile.peek();
                pile.push(partieGauche);
                
                
                String nouvelEtat = obtenirAction(etatSommet, partieGauche);
                pile.push(nouvelEtat);
                
                action = "reduce " + numeroRegle + ": " + regle;
                afficher();
                
            } else if(actionTable.equals("ACC")) {
                
                System.out.println("\nANALYSE SLR RÉUSSIE - ACCEPTATION");
                return;
                
            } else {
                
                System.out.println("\nERREUR SYNTAXIQUE ");
                System.err.println("Erreur à l'état " + etatCourant + " avec le symbole " + symboleCourant);
                return;
            }
        }
    }
    
    private String obtenirAction(String etat, String symbole) {
        int i= Integer.parseInt(etat)+1;
                
                for(int j = 0; j < tableSLR[0].length; j++) {
                    
                    if(tableSLR[0][j].equals(symbole)) {
                        return tableSLR[i][j];
                        
                    }
        }
        return "";
    }
    
    private void afficher() {
        
        StringBuilder pileStr = new StringBuilder();
        for(String elem : pile) {
            pileStr.append(elem).append(" ");
        }
        
        
        StringBuilder entreeStr = new StringBuilder();
        for(int i = index; i < tokens.size(); i++) {
            entreeStr.append(tokens.get(i)).append(" ");
        }
        
        
        System.out.printf("│ %-25s │ %-19s │ %-18s │\n", 
            tronquer(pileStr.toString(), 25),
            tronquer(entreeStr.toString(), 19),
            tronquer(action, 18));
    }
    
    
    private String tronquer(String str, int longueur) {
        if(str.length() > longueur) {
            return str.substring(0, longueur - 3) + "...";
        }
        return str;
    }
    
    public void afficherTable() {
        System.out.println("Tableau SLR");
        
        for(int i = 0; i < tableSLR.length; i++) {
            for(int j = 0; j < tableSLR[i].length && j < 10; j++) {
                System.out.printf("%8s", tableSLR[i][j] + " ");
            }
            System.out.println();
        }
    }
}
