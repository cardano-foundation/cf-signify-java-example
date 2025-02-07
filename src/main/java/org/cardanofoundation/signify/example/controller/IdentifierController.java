package org.cardanofoundation.signify.example.controller;

import lombok.*;
import org.cardanofoundation.signify.app.aiding.*;
import org.cardanofoundation.signify.app.clienting.SignifyClient;
import org.cardanofoundation.signify.app.coring.Operation;
import org.cardanofoundation.signify.cesr.exceptions.LibsodiumException;
import org.cardanofoundation.signify.core.States;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.DigestException;
import java.util.concurrent.ExecutionException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/example/identifiers")
public class IdentifierController {

    public static final String DEFAULT_ROLE = "agent";
    private final SignifyClient client;

    @PostMapping("/{name}")
    public Object createIdentifier(@RequestBody CreateIdentifierArgs createIdentifierArgs, @PathVariable String name) throws LibsodiumException, IOException, InterruptedException, DigestException {
        Operation op = Operation.fromObject(client.identifiers().create(name, createIdentifierArgs).op());
        op = client.operations().wait(op);

        return client.identifiers().addEndRole(name, DEFAULT_ROLE, client.getAgent().getPre(), null).op();
    }

    @GetMapping("/{name}")
    public States.HabState getIdentifier(@PathVariable String name) throws LibsodiumException, IOException, InterruptedException {
        return client.identifiers().get(name);
    }

    @GetMapping("")
    public IdentifierListResponse getIdentifierList() throws LibsodiumException, IOException, InterruptedException {
        return client.identifiers().list();
    }

    @PutMapping("/interact")
    public Object interactWithIdentifier(@RequestBody IdentifierInteractRequest request) throws LibsodiumException, IOException, InterruptedException, DigestException {
        return client.identifiers().interact(request.getName(), request.getPre()).op();
    }

    @PutMapping("/{name}/rotate")
    public Object rotateIdentifier(@PathVariable String name) throws LibsodiumException, DigestException, IOException, ExecutionException, InterruptedException {
        return client.identifiers().rotate(name).op();
    }

    @PutMapping("/{name}/update")
    public States.HabState updateIdentifier(@PathVariable String name, @RequestBody IdentifierInfo info) throws LibsodiumException, IOException, InterruptedException {
        return client.identifiers().update(name, info);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdentifierInteractRequest {
        private String name;
        private String pre;
    }

}
