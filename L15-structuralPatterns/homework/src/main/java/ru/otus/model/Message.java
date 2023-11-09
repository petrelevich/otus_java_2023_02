package ru.otus.model;


import java.util.List;

public class Message {
    private final long id;
    private final String field1;
    private final String field2;
    private final String field3;
    private final String field4;
    private final String field5;
    private final String field6;
    private final String field7;
    private final String field8;
    private final String field9;
    private final String field10;
    private final String field11;
    private static String field12;
    private static ObjectForMessage field13;



    //todo: 1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)

    public Message(long id, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, ObjectForMessage field13) {
        this.id = id;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
        this.field7 = field7;
        this.field8 = field8;
        this.field9 = field9;
        this.field10 = field10;
        this.field11 = field11;
        Message.field12 = field12;
        Message.field13 = field13;
    }



    public long getId() {
        return id;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }

    public String getField4() {
        return field4;
    }

    public String getField5() {
        return field5;
    }

    public String getField6() {
        return field6;
    }

    public String getField7() {
        return field7;
    }

    public String getField8() {
        return field8;
    }

    public String getField9() {
        return field9;
    }

    public String getField10() {
        return field10;
    }
    public String getField11() {
        return field11;
    }
    public String getField12() {
        return field12;
    }
    public ObjectForMessage getField13() {
        return field13;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id == message.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public Builder toBuilder() {
        ObjectForMessage newFiled13 =null;
        if (field13!=null){
            newFiled13 =new ObjectForMessage();
            newFiled13.setData(List.copyOf(field13.getData()));
        }
        return new Builder(id, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10,field11,field12,field13);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", field4='" + field4 + '\'' +
                ", field5='" + field5 + '\'' +
                ", field6='" + field6 + '\'' +
                ", field7='" + field7 + '\'' +
                ", field8='" + field8 + '\'' +
                ", field9='" + field9 + '\'' +
                ", field10='" + field10 + '\'' +
                ", field11='" + field11 + '\'' +
                ", field12='" + field12 + '\'' +
                ", field13='" + field13 + '\'' +
                '}';
    }

    public static class Builder {
        private long id;
        private String field1;
        private String field2;
        private String field3;
        private String field4;
        private String field5;
        private String field6;
        private String field7;
        private String field8;
        private String field9;
        private String field10;
        private String field11;
        private String field12;
        private ObjectForMessage field13;

        private Builder(long id, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11,String field12, ObjectForMessage field13) {
            this.id = id;
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
            this.field5 = field5;
            this.field6 = field6;
            this.field7 = field7;
            this.field8 = field8;
            this.field9 = field9;
            this.field10 = field10;
            this.field11 = field11;
            this.field12 = field12;
            this.field13 = field13;

        }

        public Builder(long id) {
        }

        public Builder field1(String field1) {
            this.field1 = field1;
            return this;
        }

        public Builder field2(String field2) {
            this.field2 = field2;
            return this;
        }

        public Builder field3(String field3) {
            this.field3 = field3;
            return this;
        }

        public Builder field4(String field4) {
            this.field4 = field4;
            return this;
        }

        public Builder field5(String field5) {
            this.field5 = field5;
            return this;
        }

        public Builder field6(String field6) {
            this.field6 = field6;
            return this;
        }

        public Builder field7(String field7) {
            this.field7 = field7;
            return this;
        }

        public Builder field8(String field8) {
            this.field8 = field8;
            return this;
        }

        public Builder field9(String field9) {
            this.field9 = field9;
            return this;
        }

        public Builder field10(String field10) {
            this.field10 = field10;
            return this;
        }
        public Builder field11(String field11) {
            this.field11 = field11;
            return this;
        }
        public Builder field12(String field12) {
            this.field12 = field12;
            return this;
        }
        public Builder field13(ObjectForMessage field13) {
            this.field13 = field13;
            return this;
        }
        public Message build() {
            return new Message(id, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13);
        }


    }
}