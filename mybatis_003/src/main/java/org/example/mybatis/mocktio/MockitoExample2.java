package org.example.mybatis.mocktio;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * @author DeYou
 * @date 2022/9/22
 */
public class MockitoExample2 {
    @Mock
    private List mockList;

    /**
     * 基类初始化mocklist
     */
    public MockitoExample2(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void shorthand(){
        mockList.add( 1 );
        verify( mockList ).add( 1 );
    }
}