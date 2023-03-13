package demo.generics.bounds.entries;

import java.util.Objects;

public class HomeCat extends Cat {
    private final String name;

    private final Integer id;

    private final Long passNo;

    public HomeCat( String name ) {
        id = 15;
        passNo = 124235145345l;
        this.name = name;
    }

    public void sitOnBoss() {

    }

    @Override
    public String toString() {
        return "HomeCat, name:" + name;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        HomeCat homeCat = (HomeCat) o;

        if ( ! Objects.equals( name, homeCat.name ) ) return false;
        if ( ! Objects.equals( id, homeCat.id ) ) return false;
        return Objects.equals( passNo, homeCat.passNo );
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + ( id != null ? id.hashCode() : 0 );
        result = 31 * result + ( passNo != null ? passNo.hashCode() : 0 );
        return result;
    }
}
