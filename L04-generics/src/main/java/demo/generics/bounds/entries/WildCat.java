package demo.generics.bounds.entries;

import java.util.Objects;

public class WildCat extends Cat {
    private String name;

    public WildCat( String name ) {
        this.name = name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WildCat, name:" + name;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        WildCat wildCat = (WildCat) o;

        return Objects.equals( name, wildCat.name );
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
