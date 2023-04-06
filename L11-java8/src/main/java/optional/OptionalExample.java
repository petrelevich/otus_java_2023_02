package optional;

import stream.Student;

import java.util.Optional;

public class OptionalExample {

    public static void main( String[] args ) {
        beforeOptional();
        optionalIncorrect();
        optionalCorrect();
    }


    private static void beforeOptional() {
        var student = getStudent();
        if ( student != null ) {
            var name = student.name();
            if ( name != null ) {
                name = name.toUpperCase();
                System.out.println( "Student: " + name );
            }
        }
    }

    private static void optionalIncorrect() {
        var student = Optional.ofNullable( getStudent() );
        if ( student.isPresent() ) {
            var name = student.get().name();
            if ( name != null ) {
                name = name.toUpperCase();
                System.out.println( "Student: " + name );
            }
        } else {
            throw new RuntimeException( "Student not found" );
        }
    }

    private static void optionalCorrect() {
        var student = Optional.ofNullable( getStudent() );

        student
                .map( Student::name )
                .map( String::toUpperCase )
                .ifPresentOrElse(
                        name -> System.out.println( "Student: " + name ),
                        () -> {
                            throw new RuntimeException();
                        }
                );
    }

    private static Student getStudent() {
        return new Student( "Maria", 22, 5, 3.5 );
    }


}
