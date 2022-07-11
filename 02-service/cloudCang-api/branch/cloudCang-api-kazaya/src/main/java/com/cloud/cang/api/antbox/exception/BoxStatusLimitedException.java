package com.cloud.cang.api.antbox.exception;

import com.cloud.cang.api.antbox.constant.BoxStatus;

public class BoxStatusLimitedException extends AntboxServerException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BoxStatusLimitedException(String msg, BoxStatus expected, BoxStatus actual) {
        super(msg + ", device status expected: " + expected.name() + " but " + actual.name());
    }

    public BoxStatusLimitedException(String msg, BoxStatus[] expected, BoxStatus actual) {
        super(msg + ", device status expected: " + toStr(expected) + " but " + actual.name());
    }

    private static String toStr(BoxStatus[] statusList) {
        StringBuilder sb = new StringBuilder();
        for (BoxStatus st : statusList) {
            sb.append(st.name()).append("|");
        }

        if (statusList.length > 1)
            sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
