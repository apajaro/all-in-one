package com.apajaro.platform.controller;

import com.apajaro.platform.common.ControllerIntTest;

public class AuthControllerIntTest extends ControllerIntTest {
    /*@Test
    void loginFailsIfInvalidCredentialsAreProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType("application/json")
                        .content(asJsonString(new LoginRequest("invalid", "invalid"))))
                .andExpect(status().isForbidden())
                .andDo(result -> System.out.println(">>>>>>>>>>" + result.getResponse().getContentAsString()));
    }*/
}
