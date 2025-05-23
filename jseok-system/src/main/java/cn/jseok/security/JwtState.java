package cn.jseok.security;

public enum JwtState {

    /**
     * 过期
     */
    EXPIRED("EXPIRED"),
    /**
     * 无效(token不合法)
     */
    INVALID("INVALID"),
    /**
     * 有效的
     */
    VALID("VALID");

    private final String state;

    JwtState(String state) {
        this.state = state;
    }

    public String toString() {
        return this.state;
    }

}
