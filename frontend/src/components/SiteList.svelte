<script>
    import Bar from 'components/Bar.svelte';
    import Site from 'components/Site.svelte';

    export let context = {};

    let creating = false;
    let customerUuid = null;
    let domainUuid = null;
    let fetched = false;
    let hasSites = false;
    let sites = [];

    $: if ((context.domain != null) && (domainUuid == null)) {
        customerUuid = context.customer.uuid;
        domainUuid = context.domain.uuid;

        fetch(`/api/v1/domains/${domainUuid}/customers/${customerUuid}/sites`)
            .then((response) => response.json())
            .then((result) => {
                sites = (result) ? result : [];
                fetched = true;
                hasSites = (sites && (sites.length > 0));
            }).catch((err) => alert('Failed to fetch sites!'));
    }

    const create = () => {
        sites = [{}, ...sites];
        creating = true;
        hasSites = true;
    };

    const onCancel = () => {
        sites.splice(0, 1);
        sites = sites;
        hasSites = (sites && (sites.length > 0));
        creating = false;
    }

    const onOK = (name, address) => {
        const body = JSON.stringify({address, name});
        const headers = {'Content-Type': 'application/json'};
        const method = 'POST';
        
        fetch(`/api/v1/domains/${domainUuid}/customers/${customerUuid}/sites`, {body, headers, method})
            .then((response) => response.json())
            .then((result) => {
                sites[0].address = result.address;
                sites[0].name = result.name;
                sites[0].uuid = result.uuid;
                sites = sites;
                creating = false;
            }).catch((err) => alert('Failed to create site!'));
    };
</script>

<component>
    {#if fetched}
    <Bar {create} {creating} itemClass="Site"/>
    {#if hasSites}
    <items>
        {#each sites as site}
        <Site on:refresh {context} {site} {onCancel} {onOK}/>
        {/each}
    </items>
    {/if}
    {/if}
</component>
