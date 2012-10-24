/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.ccs.chat;

public interface Listener<T> {
    void onEvent(T event);
}
