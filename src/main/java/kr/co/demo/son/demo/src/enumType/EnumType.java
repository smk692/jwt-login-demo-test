package kr.co.demo.son.demo.src.enumType;

public class EnumType {

    // active (Y-사용, N-미사용)
    public enum YNType implements IEnumtype {
        Y       ("사용"),
        N       ("미사용");
        private final String value;
        YNType(String value) { this.value = value; }

        @Override
        public String getKey() {
            return name();
        }
        @Override
        public String getValue(){
            return value;
        }
    }

    // active (Y-사용, N-미사용)
    public enum MobileCertificationYn implements IEnumtype {
        Y       ("인증"),
        N       ("미인증");
        private final String value;
        MobileCertificationYn(String value) { this.value = value; }

        @Override
        public String getKey() {
            return name();
        }
        @Override
        public String getValue(){
            return value;
        }
    }
}
