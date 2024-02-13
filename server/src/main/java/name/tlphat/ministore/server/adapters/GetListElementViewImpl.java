package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.usecases.constants.GetListElementError;
import name.tlphat.ministore.server.usecases.ports.GetListElementView;

public class GetListElementViewImpl implements GetListElementView {

    @Override
    public String prepareSuccessfulView(String data) {
        return data;
    }

    @Override
    public String prepareFailedView(GetListElementError error) {
        return error.toString();
    }
}
