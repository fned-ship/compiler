


public enum Categorie {
    
    EOF,         
    ID,            
    NOMBRE, 
    IF,            
    THEN,          
    ELSE,    
    PLUS,          
    MOINS,         
    MULT,          
    DIV,           
    AFFECT, 
    PARO,          
    PARF,          
    ACCO,          
    ACCF,          
    PV;    
               

    public String toString() {
        return this.name().toLowerCase();
    }

    public static Categorie toCategorie(String s) {
        for(Categorie c : Categorie.values())
            if(c.toString().equalsIgnoreCase(s))
                return c;
        return null;
    }

    public boolean estTerminal() {
        return ordinal() <= PV.ordinal();
    }

    public boolean estNonTerminal() {
        return ordinal() > PV.ordinal();
    }
}
