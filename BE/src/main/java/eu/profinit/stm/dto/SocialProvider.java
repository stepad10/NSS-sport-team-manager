package eu.profinit.stm.dto;

/**
 * @author Chinna
 * @since 26/3/18
 */
public enum SocialProvider {

    FACEBOOK("facebook"),
	GOOGLE("google"),
	GITHUB("github"),
	//TWITTER("twitter"),
	LINKEDIN("linkedin"),
	LOCAL("local");

    private final String providerType;

    public String getProviderType() {
        return providerType;
    }

    SocialProvider(final String providerType) {
        this.providerType = providerType;
    }

}
