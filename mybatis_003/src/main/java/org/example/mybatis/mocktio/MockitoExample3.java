package org.example.mybatis.mocktio;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author DeYou
 * @date 2022/9/22
 */
@RunWith( MockitoJUnitRunner.class )
public class MockitoExample3 {
    @Mock
    private List mockList;

    /**
     * 基类初始化mocklist
     */

    @Test
    public void shorthand(){
        mockList.add( 1 );
        verify( mockList ).add( 1 );
    }

    @Test
    public void with_arguments(){
        Comparable comparable = mock(Comparable.class);
        //预设根据不同的参数返回不同的结果
        when(comparable.compareTo("Test")).thenReturn(1);
        when(comparable.compareTo("Omg")).thenReturn(2);
        assertEquals(1, comparable.compareTo("Test"));
        assertEquals(2, comparable.compareTo("Omg"));
        //对于没有预设的情况会返回默认值
        assertEquals(0, comparable.compareTo("Not stub"));
    }
}