package org.example.mybatis.mocktio;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author DeYou
 * @date 2022/9/22
 */

public class MockitoExample4 {
    @Test
    public void with_unspecified_arguments(){
        List list = mock(List.class);
        //匹配任意参数
        when(list.get(anyInt())).thenReturn(1);
        when(list.contains(argThat(new IsValid()))).thenReturn(true);
        assertEquals(1, list.get(1));
        assertEquals(1, list.get(999));
        assertTrue(list.contains(1));
        assertTrue(!list.contains(3));
    }

    private class IsValid extends ArgumentMatcher<List> {
        @Override
        public boolean matches(Object o) {
            return o .equals( 1 )  || o.equals( 2);
        }
    }
    @Test
    public void all_arguments_provided_by_matchers(){
        Comparator comparator = mock(Comparator.class);
        comparator.compare("nihao","hello");
        //如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
        verify(comparator).compare(anyString(),eq("hello"));
        //下面的为无效的参数匹配使用
        //verify(comparator).compare(anyString(),"hello");
    }

}