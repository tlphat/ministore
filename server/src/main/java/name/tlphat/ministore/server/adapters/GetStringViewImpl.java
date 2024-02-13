package name.tlphat.ministore.server.adapters;

import name.tlphat.ministore.server.usecases.constants.GetStringError;
import name.tlphat.ministore.server.usecases.ports.GetStringView;

public class GetStringViewImpl implements GetStringView {

    @Override
    public String prepareSuccessfulView(String response) {
        return response;
    }

    @Override
    public String prepareFailedView(GetStringError error) {
        return error.toString();
    }
}
