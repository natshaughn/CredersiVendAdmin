<script>
    import Bar from 'components/Bar.svelte';
    import Customer from 'components/Customer.svelte';

    export let context = {};

    let creating = false;
    let customers = [];
    let domainUuid = null;
    let fetched = false;
    let hasCustomers = false;

    $: if ((context.domain != null) && (domainUuid == null)) {
        domainUuid = context.domain.uuid;
        fetch(`/api/v1/domains/${domainUuid}/customers`)
            .then((response) => response.json())
            .then((result) => {
                customers = (result) ? result : [];
                fetched = true;
                hasCustomers = (customers && (customers.length > 0));
            }).catch((err) => alert('Failed to fetch customers!'));
    }

    const create = () => {
        customers = [{}, ...customers];
        creating = true;
        hasCustomers = true;
    };

    const onCancel = () => {
        customers.splice(0, 1);
        customers = customers;
        hasCustomers = (customers && (customers.length > 0));
        creating = false;
    }

    const onOK = (name) => {
        const body = JSON.stringify({name});
        const headers = {'Content-Type': 'application/json'};
        const method = 'POST';
        
        fetch(`/api/v1/domains/${domainUuid}/customers`, {body, headers, method})
            .then((response) => response.json())
            .then((result) => {
                customers[0].name = result.name;
                customers[0].uuid = result.uuid;
                customers = customers;
                creating = false;
            }).catch((err) => alert('Failed to create customer!'));
    };
</script>

<component>
    {#if fetched}
    <Bar {create} {creating} itemClass="Customer"/>
    {#if hasCustomers}
    <items>
        {#each customers as customer}
        <Customer on:refresh {context} {customer} {onCancel} {onOK}/>
        {/each}
    </items>
    {/if}
    {/if}
</component>
