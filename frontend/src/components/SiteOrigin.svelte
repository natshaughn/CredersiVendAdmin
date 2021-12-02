<script>
    import Bar from 'components/Bar.svelte';
    import Machine from 'components/Machine.svelte';

    export let context = {};

    let creating = false;
    let customerUuid = null;
    let domainUuid = null;
    let fetched = false;
    let machine = null;
    let siteUuid = null;
    
    $: if ((context.domain != null) && (domainUuid == null)) {
        customerUuid = context.customer.uuid;
        domainUuid = context.domain.uuid;
        siteUuid = context.site.uuid;

        fetch(`/api/v1/domains/${domainUuid}/customers/${customerUuid}/sites/${siteUuid}/origin`)
            .then((response) => response.json())
            .then((result) => {
                fetched = true;
                if (result && result.uuid) {
                    machine = result;
                }
            }).catch((err) => alert('Failed to fetch route origin machine!'));
    }

    const create = () => {
        machine = {name: ''};
        creating = true;
    };

    const onCancel = () => {
        machine = null;
        creating = false;
    }

    const onOK = (name, location) => {
        const body = JSON.stringify({name, location});
        const headers = {'Content-Type': 'application/json'};
        const method = 'POST';
        
        fetch(`/api/v1/domains/${domainUuid}/customers/${customerUuid}/sites/${siteUuid}/origin`, {body, headers, method})
            .then((response) => response.json())
            .then((result) => {
                creating = false;
                machine = {
                    name: result.name,
                    location: result.location,
                    uuid: result.uuid
                };
            }).catch((err) => alert('Failed to create site route origin machine!'));
    };
</script>

<component>
    {#if fetched}
    {#if !machine}
    <Bar {create} {creating} itemClass="Machine"/>
    {:else}
    <Machine on:refresh {context} {machine} {onCancel} {onOK}/>
    {/if}
    {/if}
</component>
