package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.usecases.constants.DeleteStringError;
import name.tlphat.ministore.server.usecases.ports.DeleteStringView;

public class DeleteStringViewImpl implements DeleteStringView {

    @Override
    public String prepareSuccessfulView() {
        return "OK";
    }

    @Override
    public String prepareFailedView(DeleteStringError error) {
        return error.toString();
    }
}
