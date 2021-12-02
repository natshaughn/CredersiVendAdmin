<script>
    export let context = {};
</script>

<style type="text/scss">
    @import 'src/styles/vars';

	h1 {
		margin: 0;
	}

	header {
        display: flex;
		flex-direction: row;
        flex-grow: 0;
		width: 100%;
	}

    heading {
        flex-grow: 1;
    }

    heading subheading {
        padding-left: $padding;
    }

	.logo {
		height: $logoSize;
		width: $logoSize;
	}
</style>

<header>
    <heading>
        <h1>Credersi-vend Admin Frontend</h1>
        <subheading>
            {#if context.domain}
                Connected to the {context.domain.name} domain
                {#if context.domain.environment}
                    &nbsp;({context.domain.environment} environment)
                {/if}
            {/if}
        </subheading>
    </heading>
    <img alt="Credersi Logo" class="logo" src="images/CredersiLogo.png"/>
</header>