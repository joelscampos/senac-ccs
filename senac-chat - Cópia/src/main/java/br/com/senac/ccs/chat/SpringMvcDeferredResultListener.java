/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.ccs.chat;

import org.springframework.web.context.request.async.DeferredResult;

public class SpringMvcDeferredResultListener<T> implements Listener<T>  {

    private DeferredResult<T> deferredResult;

    public SpringMvcDeferredResultListener() {
        this.deferredResult = new DeferredResult<T>();
    }

    @Override
    public void onEvent(T result) {
        deferredResult.setResult(result);
    }

    public DeferredResult<T> getDeferredResult() {
        return deferredResult;
    }

}